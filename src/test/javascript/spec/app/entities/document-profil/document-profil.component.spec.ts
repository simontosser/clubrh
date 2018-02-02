/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CvthequeTestModule } from '../../../test.module';
import { DocumentProfilComponent } from '../../../../../../main/webapp/app/entities/document-profil/document-profil.component';
import { DocumentProfilService } from '../../../../../../main/webapp/app/entities/document-profil/document-profil.service';
import { DocumentProfil } from '../../../../../../main/webapp/app/entities/document-profil/document-profil.model';

describe('Component Tests', () => {

    describe('DocumentProfil Management Component', () => {
        let comp: DocumentProfilComponent;
        let fixture: ComponentFixture<DocumentProfilComponent>;
        let service: DocumentProfilService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CvthequeTestModule],
                declarations: [DocumentProfilComponent],
                providers: [
                    DocumentProfilService
                ]
            })
            .overrideTemplate(DocumentProfilComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DocumentProfilComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentProfilService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DocumentProfil(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.documentProfils[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
