/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CvthequeTestModule } from '../../../test.module';
import { DocumentProfilDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/document-profil/document-profil-delete-dialog.component';
import { DocumentProfilService } from '../../../../../../main/webapp/app/entities/document-profil/document-profil.service';

describe('Component Tests', () => {

    describe('DocumentProfil Management Delete Component', () => {
        let comp: DocumentProfilDeleteDialogComponent;
        let fixture: ComponentFixture<DocumentProfilDeleteDialogComponent>;
        let service: DocumentProfilService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CvthequeTestModule],
                declarations: [DocumentProfilDeleteDialogComponent],
                providers: [
                    DocumentProfilService
                ]
            })
            .overrideTemplate(DocumentProfilDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DocumentProfilDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentProfilService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
