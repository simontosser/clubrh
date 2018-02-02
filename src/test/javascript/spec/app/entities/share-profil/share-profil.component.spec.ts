/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CvthequeTestModule } from '../../../test.module';
import { ShareProfilComponent } from '../../../../../../main/webapp/app/entities/share-profil/share-profil.component';
import { ShareProfilService } from '../../../../../../main/webapp/app/entities/share-profil/share-profil.service';
import { ShareProfil } from '../../../../../../main/webapp/app/entities/share-profil/share-profil.model';

describe('Component Tests', () => {

    describe('ShareProfil Management Component', () => {
        let comp: ShareProfilComponent;
        let fixture: ComponentFixture<ShareProfilComponent>;
        let service: ShareProfilService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CvthequeTestModule],
                declarations: [ShareProfilComponent],
                providers: [
                    ShareProfilService
                ]
            })
            .overrideTemplate(ShareProfilComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShareProfilComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShareProfilService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ShareProfil(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.shareProfils[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
